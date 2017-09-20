import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { FilterType } from './filter-type.model';
import { FilterTypePopupService } from './filter-type-popup.service';
import { FilterTypeService } from './filter-type.service';
import { Filter, FilterService } from '../filter';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-filter-type-dialog',
    templateUrl: './filter-type-dialog.component.html'
})
export class FilterTypeDialogComponent implements OnInit {

    filterType: FilterType;
    isSaving: boolean;

    filters: Filter[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private filterTypeService: FilterTypeService,
        private filterService: FilterService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.filterService.query()
            .subscribe((res: ResponseWrapper) => { this.filters = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.filterType.id !== undefined) {
            this.subscribeToSaveResponse(
                this.filterTypeService.update(this.filterType));
        } else {
            this.subscribeToSaveResponse(
                this.filterTypeService.create(this.filterType));
        }
    }

    private subscribeToSaveResponse(result: Observable<FilterType>) {
        result.subscribe((res: FilterType) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: FilterType) {
        this.eventManager.broadcast({ name: 'filterTypeListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.alertService.error(error.message, null, null);
    }

    trackFilterById(index: number, item: Filter) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-filter-type-popup',
    template: ''
})
export class FilterTypePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private filterTypePopupService: FilterTypePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.filterTypePopupService
                    .open(FilterTypeDialogComponent as Component, params['id']);
            } else {
                this.filterTypePopupService
                    .open(FilterTypeDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

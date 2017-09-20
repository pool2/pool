import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { FilterBrand } from './filter-brand.model';
import { FilterBrandPopupService } from './filter-brand-popup.service';
import { FilterBrandService } from './filter-brand.service';
import { Filter, FilterService } from '../filter';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-filter-brand-dialog',
    templateUrl: './filter-brand-dialog.component.html'
})
export class FilterBrandDialogComponent implements OnInit {

    filterBrand: FilterBrand;
    isSaving: boolean;

    filters: Filter[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private filterBrandService: FilterBrandService,
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
        if (this.filterBrand.id !== undefined) {
            this.subscribeToSaveResponse(
                this.filterBrandService.update(this.filterBrand));
        } else {
            this.subscribeToSaveResponse(
                this.filterBrandService.create(this.filterBrand));
        }
    }

    private subscribeToSaveResponse(result: Observable<FilterBrand>) {
        result.subscribe((res: FilterBrand) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: FilterBrand) {
        this.eventManager.broadcast({ name: 'filterBrandListModification', content: 'OK'});
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
    selector: 'jhi-filter-brand-popup',
    template: ''
})
export class FilterBrandPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private filterBrandPopupService: FilterBrandPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.filterBrandPopupService
                    .open(FilterBrandDialogComponent as Component, params['id']);
            } else {
                this.filterBrandPopupService
                    .open(FilterBrandDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

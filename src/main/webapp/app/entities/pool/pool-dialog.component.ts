import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Pool } from './pool.model';
import { PoolPopupService } from './pool-popup.service';
import { PoolService } from './pool.service';
import { Customer, CustomerService } from '../customer';
import { Filter, FilterService } from '../filter';
import { Material, MaterialService } from '../material';
import { Note, NoteService } from '../note';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-pool-dialog',
    templateUrl: './pool-dialog.component.html'
})
export class PoolDialogComponent implements OnInit {

    pool: Pool;
    isSaving: boolean;

    customers: Customer[];

    filters: Filter[];

    materials: Material[];

    notes: Note[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private poolService: PoolService,
        private customerService: CustomerService,
        private filterService: FilterService,
        private materialService: MaterialService,
        private noteService: NoteService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.customerService.query()
            .subscribe((res: ResponseWrapper) => { this.customers = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.filterService
            .query({filter: 'pool-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.pool.filter || !this.pool.filter.id) {
                    this.filters = res.json;
                } else {
                    this.filterService
                        .find(this.pool.filter.id)
                        .subscribe((subRes: Filter) => {
                            this.filters = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
        this.materialService.query()
            .subscribe((res: ResponseWrapper) => { this.materials = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.noteService.query()
            .subscribe((res: ResponseWrapper) => { this.notes = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {

        if (this.pool.customer === undefined) {
            this.pool.customer = this.customerService.getCustomer();
        }

        this.isSaving = true;
        if (this.pool.id !== undefined) {
            this.subscribeToSaveResponse(
                this.poolService.update(this.pool));
        } else {
            this.subscribeToSaveResponse(
                this.poolService.create(this.pool));
        }
    }

    private subscribeToSaveResponse(result: Observable<Pool>) {
        result.subscribe((res: Pool) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Pool) {
        this.eventManager.broadcast({ name: 'poolListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.alertService.error(error.message, null, null);
    }

    trackCustomerById(index: number, item: Customer) {
        return item.id;
    }

    trackFilterById(index: number, item: Filter) {
        return item.id;
    }

    trackMaterialById(index: number, item: Material) {
        return item.id;
    }

    trackNoteById(index: number, item: Note) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-pool-popup',
    template: ''
})
export class PoolPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private poolPopupService: PoolPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.poolPopupService
                    .open(PoolDialogComponent as Component, params['id']);
            } else {
                this.poolPopupService
                    .open(PoolDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

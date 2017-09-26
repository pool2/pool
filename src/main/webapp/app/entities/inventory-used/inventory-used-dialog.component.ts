import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { InventoryUsed } from './inventory-used.model';
import { InventoryUsedPopupService } from './inventory-used-popup.service';
import { InventoryUsedService } from './inventory-used.service';
import { InventoryItem, InventoryItemService } from '../inventory-item';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-inventory-used-dialog',
    templateUrl: './inventory-used-dialog.component.html'
})
export class InventoryUsedDialogComponent implements OnInit {

    inventoryUsed: InventoryUsed;
    isSaving: boolean;

    inventoryitems: InventoryItem[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private inventoryUsedService: InventoryUsedService,
        private inventoryItemService: InventoryItemService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.inventoryItemService.query()
            .subscribe((res: ResponseWrapper) => { this.inventoryitems = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.inventoryUsed.id !== undefined) {
            this.subscribeToSaveResponse(
                this.inventoryUsedService.update(this.inventoryUsed));
        } else {
            this.subscribeToSaveResponse(
                this.inventoryUsedService.create(this.inventoryUsed));
        }
    }

    private subscribeToSaveResponse(result: Observable<InventoryUsed>) {
        result.subscribe((res: InventoryUsed) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: InventoryUsed) {
        this.eventManager.broadcast({ name: 'inventoryUsedListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.alertService.error(error.message, null, null);
    }

    trackInventoryItemById(index: number, item: InventoryItem) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-inventory-used-popup',
    template: ''
})
export class InventoryUsedPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private inventoryUsedPopupService: InventoryUsedPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.inventoryUsedPopupService
                    .open(InventoryUsedDialogComponent as Component, params['id']);
            } else {
                this.inventoryUsedPopupService
                    .open(InventoryUsedDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

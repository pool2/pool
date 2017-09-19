import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { InventoryItem } from './inventory-item.model';
import { InventoryItemPopupService } from './inventory-item-popup.service';
import { InventoryItemService } from './inventory-item.service';

@Component({
    selector: 'jhi-inventory-item-dialog',
    templateUrl: './inventory-item-dialog.component.html'
})
export class InventoryItemDialogComponent implements OnInit {

    inventoryItem: InventoryItem;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private inventoryItemService: InventoryItemService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.inventoryItem.id !== undefined) {
            this.subscribeToSaveResponse(
                this.inventoryItemService.update(this.inventoryItem));
        } else {
            this.subscribeToSaveResponse(
                this.inventoryItemService.create(this.inventoryItem));
        }
    }

    private subscribeToSaveResponse(result: Observable<InventoryItem>) {
        result.subscribe((res: InventoryItem) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: InventoryItem) {
        this.eventManager.broadcast({ name: 'inventoryItemListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.alertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'jhi-inventory-item-popup',
    template: ''
})
export class InventoryItemPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private inventoryItemPopupService: InventoryItemPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.inventoryItemPopupService
                    .open(InventoryItemDialogComponent as Component, params['id']);
            } else {
                this.inventoryItemPopupService
                    .open(InventoryItemDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

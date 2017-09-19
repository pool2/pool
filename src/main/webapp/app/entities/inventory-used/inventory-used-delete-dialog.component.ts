import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { InventoryUsed } from './inventory-used.model';
import { InventoryUsedPopupService } from './inventory-used-popup.service';
import { InventoryUsedService } from './inventory-used.service';

@Component({
    selector: 'jhi-inventory-used-delete-dialog',
    templateUrl: './inventory-used-delete-dialog.component.html'
})
export class InventoryUsedDeleteDialogComponent {

    inventoryUsed: InventoryUsed;

    constructor(
        private inventoryUsedService: InventoryUsedService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.inventoryUsedService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'inventoryUsedListModification',
                content: 'Deleted an inventoryUsed'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-inventory-used-delete-popup',
    template: ''
})
export class InventoryUsedDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private inventoryUsedPopupService: InventoryUsedPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.inventoryUsedPopupService
                .open(InventoryUsedDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

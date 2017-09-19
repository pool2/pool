import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { InventoryUsed } from './inventory-used.model';
import { InventoryUsedService } from './inventory-used.service';

@Injectable()
export class InventoryUsedPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private inventoryUsedService: InventoryUsedService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.inventoryUsedService.find(id).subscribe((inventoryUsed) => {
                    this.ngbModalRef = this.inventoryUsedModalRef(component, inventoryUsed);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.inventoryUsedModalRef(component, new InventoryUsed());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    inventoryUsedModalRef(component: Component, inventoryUsed: InventoryUsed): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.inventoryUsed = inventoryUsed;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}

import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { WaterTest } from './water-test.model';
import { WaterTestService } from './water-test.service';

@Injectable()
export class WaterTestPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private waterTestService: WaterTestService

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
                this.waterTestService.find(id).subscribe((waterTest) => {
                    if (waterTest.dateTime) {
                        waterTest.dateTime = {
                            year: waterTest.dateTime.getFullYear(),
                            month: waterTest.dateTime.getMonth() + 1,
                            day: waterTest.dateTime.getDate()
                        };
                    }
                    this.ngbModalRef = this.waterTestModalRef(component, waterTest);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.waterTestModalRef(component, new WaterTest());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    waterTestModalRef(component: Component, waterTest: WaterTest): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.waterTest = waterTest;
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

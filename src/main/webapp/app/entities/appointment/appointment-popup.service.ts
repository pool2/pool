import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Appointment } from './appointment.model';
import { AppointmentService } from './appointment.service';

@Injectable()
export class AppointmentPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private appointmentService: AppointmentService

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
                this.appointmentService.find(id).subscribe((appointment) => {
                    if (appointment.startTime) {
                        appointment.startTime = {
                            year: appointment.startTime.getFullYear(),
                            month: appointment.startTime.getMonth() + 1,
                            day: appointment.startTime.getDate()
                        };
                    }
                    if (appointment.endTime) {
                        appointment.endTime = {
                            year: appointment.endTime.getFullYear(),
                            month: appointment.endTime.getMonth() + 1,
                            day: appointment.endTime.getDate()
                        };
                    }
                    this.ngbModalRef = this.appointmentModalRef(component, appointment);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.appointmentModalRef(component, new Appointment());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    appointmentModalRef(component: Component, appointment: Appointment): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.appointment = appointment;
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

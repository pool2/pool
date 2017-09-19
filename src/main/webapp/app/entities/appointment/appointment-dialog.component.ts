import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { Appointment } from './appointment.model';
import { AppointmentPopupService } from './appointment-popup.service';
import { AppointmentService } from './appointment.service';
import { Image, ImageService } from '../image';
import { Employee, EmployeeService } from '../employee';
import { Pool, PoolService } from '../pool';
import { InventoryUsed, InventoryUsedService } from '../inventory-used';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-appointment-dialog',
    templateUrl: './appointment-dialog.component.html'
})
export class AppointmentDialogComponent implements OnInit {

    appointment: Appointment;
    isSaving: boolean;

    images: Image[];

    employees: Employee[];

    pools: Pool[];

    inventoryuseds: InventoryUsed[];
    startTimeDp: any;
    endTimeDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private dataUtils: JhiDataUtils,
        private alertService: JhiAlertService,
        private appointmentService: AppointmentService,
        private imageService: ImageService,
        private employeeService: EmployeeService,
        private poolService: PoolService,
        private inventoryUsedService: InventoryUsedService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.imageService
            .query({filter: 'appointment-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.appointment.image || !this.appointment.image.id) {
                    this.images = res.json;
                } else {
                    this.imageService
                        .find(this.appointment.image.id)
                        .subscribe((subRes: Image) => {
                            this.images = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
        this.employeeService.query()
            .subscribe((res: ResponseWrapper) => { this.employees = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.poolService.query()
            .subscribe((res: ResponseWrapper) => { this.pools = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.inventoryUsedService.query()
            .subscribe((res: ResponseWrapper) => { this.inventoryuseds = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.appointment.id !== undefined) {
            this.subscribeToSaveResponse(
                this.appointmentService.update(this.appointment));
        } else {
            this.subscribeToSaveResponse(
                this.appointmentService.create(this.appointment));
        }
    }

    private subscribeToSaveResponse(result: Observable<Appointment>) {
        result.subscribe((res: Appointment) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Appointment) {
        this.eventManager.broadcast({ name: 'appointmentListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.alertService.error(error.message, null, null);
    }

    trackImageById(index: number, item: Image) {
        return item.id;
    }

    trackEmployeeById(index: number, item: Employee) {
        return item.id;
    }

    trackPoolById(index: number, item: Pool) {
        return item.id;
    }

    trackInventoryUsedById(index: number, item: InventoryUsed) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}

@Component({
    selector: 'jhi-appointment-popup',
    template: ''
})
export class AppointmentPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private appointmentPopupService: AppointmentPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.appointmentPopupService
                    .open(AppointmentDialogComponent as Component, params['id']);
            } else {
                this.appointmentPopupService
                    .open(AppointmentDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

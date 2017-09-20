import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { WaterTest } from './water-test.model';
import { WaterTestPopupService } from './water-test-popup.service';
import { WaterTestService } from './water-test.service';
import { Appointment, AppointmentService } from '../appointment';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-water-test-dialog',
    templateUrl: './water-test-dialog.component.html'
})
export class WaterTestDialogComponent implements OnInit {

    waterTest: WaterTest;
    isSaving: boolean;

    appointments: Appointment[];
    dateTimeDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private waterTestService: WaterTestService,
        private appointmentService: AppointmentService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.appointmentService.query()
            .subscribe((res: ResponseWrapper) => { this.appointments = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.waterTest.id !== undefined) {
            this.subscribeToSaveResponse(
                this.waterTestService.update(this.waterTest));
        } else {
            this.subscribeToSaveResponse(
                this.waterTestService.create(this.waterTest));
        }
    }

    private subscribeToSaveResponse(result: Observable<WaterTest>) {
        result.subscribe((res: WaterTest) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: WaterTest) {
        this.eventManager.broadcast({ name: 'waterTestListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.alertService.error(error.message, null, null);
    }

    trackAppointmentById(index: number, item: Appointment) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-water-test-popup',
    template: ''
})
export class WaterTestPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private waterTestPopupService: WaterTestPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.waterTestPopupService
                    .open(WaterTestDialogComponent as Component, params['id']);
            } else {
                this.waterTestPopupService
                    .open(WaterTestDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

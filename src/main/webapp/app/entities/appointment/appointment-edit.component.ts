import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Appointment } from './appointment.model';
import { AppointmentPopupService } from './appointment-popup.service';
import { AppointmentService } from './appointment.service';
import { WaterTest, WaterTestService } from '../water-test';
import { Employee, EmployeeService } from '../employee';
import { Pool, PoolService } from '../pool';
import { InventoryUsed, InventoryUsedService } from '../inventory-used';
import { Task, TaskService } from '../task';
import { ResponseWrapper } from '../../shared';
import { Customer } from '../customer/customer.model';
import { CustomerService } from '../customer/customer.service';
import { Principal } from '../../shared/auth/principal.service';

@Component({
    selector: 'jhi-appointment-dialog',
    templateUrl: './appointment-dialog.component.html'
})
export class AppointmentDialogComponent implements OnInit {

    appointment: Appointment;
    isSaving: boolean;

    watertests: WaterTest[];

    employees: Employee[];
    customers: Customer[];
    customer: Customer;
    employee: Employee;

    customersPools: Pool[];

    inventoryuseds: InventoryUsed[];

    tasks: Task[];
    startTimeDp: any;
    endTimeDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private appointmentService: AppointmentService,
        private waterTestService: WaterTestService,
        private employeeService: EmployeeService,
        private customerService: CustomerService,
        private poolService: PoolService,
        private inventoryUsedService: InventoryUsedService,
        private taskService: TaskService,
        private principal: Principal,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.waterTestService
            .query({filter: 'appointment-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.appointment.waterTest || !this.appointment.waterTest.id) {
                    this.watertests = res.json;
                } else {
                    this.waterTestService
                        .find(this.appointment.waterTest.id)
                        .subscribe((subRes: WaterTest) => {
                            this.watertests = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
        this.employeeService.query()
            .subscribe((res: ResponseWrapper) => { this.employees = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.inventoryUsedService.query()
            .subscribe((res: ResponseWrapper) => { this.inventoryuseds = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.taskService.query()
            .subscribe((res: ResponseWrapper) => { this.tasks = res.json; }, (res: ResponseWrapper) => this.onError(res.json));

        this.principal.identity().then((account) => {
            this.employeeService.findByUserLogin(account.login).subscribe((employee) => {
                this.employee = employee;
                this.appointment.employee = employee;
                this.customerService.findByCompanyId(employee.company.id).subscribe((customers) => {
                    if (customers) {
                        this.customer = customers[0];
                        this.changePools();
                    }
                    this.customers = customers;
                });
            });
        });
    }

    changePools() {
        this.poolService.findByCustomer(this.customer.id).subscribe((customersPools) => {
            this.customersPools = customersPools;

        });
    }

    customerChanged(event) {
        console.log(event);
        this.customer = event;
        this.changePools();
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

    trackWaterTestById(index: number, item: WaterTest) {
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

    trackTaskById(index: number, item: Task) {
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

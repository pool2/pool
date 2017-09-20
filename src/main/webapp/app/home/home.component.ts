import { Component, OnInit } from '@angular/core';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Account, LoginModalService, Principal } from '../shared';
import {Employee} from '../entities/employee/employee.model';
import {EmployeeService} from '../entities/employee/employee.service';

@Component({
    selector: 'jhi-home',
    templateUrl: './home.component.html',
    styleUrls: [
        'home.scss'
    ]

})
export class HomeComponent implements OnInit {
    account: Account;
    employee: Employee;
    modalRef: NgbModalRef;

    constructor(
        private principal: Principal,
        private loginModalService: LoginModalService,
        private eventManager: JhiEventManager,
        private employeeService: EmployeeService
    ) {
    }

    ngOnInit() {
        this.principal.identity().then((account) => {
            this.account = account;

            this.employeeService.findByUserLogin(account.login).subscribe((employee) => {
                this.employee = employee;
            });
        });
        this.registerAuthenticationSuccess();
    }

    registerAuthenticationSuccess() {
        this.eventManager.subscribe('authenticationSuccess', (message) => {
            this.principal.identity().then((account) => {
                this.account = account;
                this.employeeService.findByUserLogin(account.login).subscribe((employee) => {
                    this.employee = employee;
                });
            });
        });
    }

    isAuthenticated() {
        return this.principal.isAuthenticated();
    }

    login() {
        this.modalRef = this.loginModalService.open();
    }
}

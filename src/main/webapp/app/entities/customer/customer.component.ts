import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiAlertService } from 'ng-jhipster';

import { Customer } from './customer.model';
import { CustomerService } from './customer.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-customer',
    templateUrl: './customer.component.html'
})
export class CustomerComponent implements OnInit, OnDestroy {
customers: Customer[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private customerService: CustomerService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.customerService.query().subscribe(
            (res: ResponseWrapper) => {
                this.customers = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInCustomers();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Customer) {
        return item.id;
    }
    registerChangeInCustomers() {
        this.eventSubscriber = this.eventManager.subscribe('customerListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}

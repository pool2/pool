import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiAlertService } from 'ng-jhipster';

import { Pool } from './pool.model';
import { PoolService } from './pool.service';
import { CustomerService } from '../customer/customer.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-pool',
    templateUrl: './pool.component.html'
})
export class PoolComponent implements OnInit, OnDestroy {
pools: Pool[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private poolService: PoolService,
        private customerService: CustomerService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {

        if (this.customerService.getCustomer()) {
            this.poolService.findByCustomer(this.customerService.getCustomer().id).subscribe((pools) => {
                this.pools = pools;
            });
        } else {
            this.poolService.query().subscribe(
                (res: ResponseWrapper) => {
                    this.pools = res.json;
                 },
                (res: ResponseWrapper) => this.onError(res.json)
            );
        }
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInPools();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Pool) {
        return item.id;
    }
    registerChangeInPools() {
        this.eventSubscriber = this.eventManager.subscribe('poolListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}

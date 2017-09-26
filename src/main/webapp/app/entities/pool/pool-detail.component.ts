import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { Pool } from './pool.model';
import { PoolService } from './pool.service';
import { Customer } from '../customer/customer.model';

@Component({
    selector: 'jhi-pool-detail',
    templateUrl: './pool-detail.component.html'
})
export class PoolDetailComponent implements OnInit, OnDestroy {

    pool: Pool;
    private subscription: Subscription;
    private eventSubscriber: Subscription;
    private customer: Customer;

    constructor(
        private eventManager: JhiEventManager,
        private poolService: PoolService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPools();

    }

    load(id) {
        this.poolService.find(id).subscribe((pool) => {
            this.pool = pool;
        });

    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPools() {
        this.eventSubscriber = this.eventManager.subscribe(
            'poolListModification',
            (response) => this.load(this.pool.id)
        );
    }
}

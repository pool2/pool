import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiAlertService } from 'ng-jhipster';

import { WaterTest } from './water-test.model';
import { WaterTestService } from './water-test.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-water-test',
    templateUrl: './water-test.component.html'
})
export class WaterTestComponent implements OnInit, OnDestroy {
waterTests: WaterTest[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private waterTestService: WaterTestService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.waterTestService.query().subscribe(
            (res: ResponseWrapper) => {
                this.waterTests = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInWaterTests();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: WaterTest) {
        return item.id;
    }
    registerChangeInWaterTests() {
        this.eventSubscriber = this.eventManager.subscribe('waterTestListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}

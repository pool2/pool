import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiAlertService } from 'ng-jhipster';

import { InventoryUsed } from './inventory-used.model';
import { InventoryUsedService } from './inventory-used.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-inventory-used',
    templateUrl: './inventory-used.component.html'
})
export class InventoryUsedComponent implements OnInit, OnDestroy {
inventoryUseds: InventoryUsed[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private inventoryUsedService: InventoryUsedService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.inventoryUsedService.query().subscribe(
            (res: ResponseWrapper) => {
                this.inventoryUseds = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInInventoryUseds();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: InventoryUsed) {
        return item.id;
    }
    registerChangeInInventoryUseds() {
        this.eventSubscriber = this.eventManager.subscribe('inventoryUsedListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}

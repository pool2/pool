import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiAlertService } from 'ng-jhipster';

import { InventoryItem } from './inventory-item.model';
import { InventoryItemService } from './inventory-item.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-inventory-item',
    templateUrl: './inventory-item.component.html'
})
export class InventoryItemComponent implements OnInit, OnDestroy {
inventoryItems: InventoryItem[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private inventoryItemService: InventoryItemService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.inventoryItemService.query().subscribe(
            (res: ResponseWrapper) => {
                this.inventoryItems = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInInventoryItems();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: InventoryItem) {
        return item.id;
    }
    registerChangeInInventoryItems() {
        this.eventSubscriber = this.eventManager.subscribe('inventoryItemListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}

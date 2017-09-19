import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { InventoryItem } from './inventory-item.model';
import { InventoryItemService } from './inventory-item.service';

@Component({
    selector: 'jhi-inventory-item-detail',
    templateUrl: './inventory-item-detail.component.html'
})
export class InventoryItemDetailComponent implements OnInit, OnDestroy {

    inventoryItem: InventoryItem;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private inventoryItemService: InventoryItemService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInInventoryItems();
    }

    load(id) {
        this.inventoryItemService.find(id).subscribe((inventoryItem) => {
            this.inventoryItem = inventoryItem;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInInventoryItems() {
        this.eventSubscriber = this.eventManager.subscribe(
            'inventoryItemListModification',
            (response) => this.load(this.inventoryItem.id)
        );
    }
}

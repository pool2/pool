import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiAlertService } from 'ng-jhipster';

import { FilterBrand } from './filter-brand.model';
import { FilterBrandService } from './filter-brand.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-filter-brand',
    templateUrl: './filter-brand.component.html'
})
export class FilterBrandComponent implements OnInit, OnDestroy {
filterBrands: FilterBrand[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private filterBrandService: FilterBrandService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.filterBrandService.query().subscribe(
            (res: ResponseWrapper) => {
                this.filterBrands = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInFilterBrands();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: FilterBrand) {
        return item.id;
    }
    registerChangeInFilterBrands() {
        this.eventSubscriber = this.eventManager.subscribe('filterBrandListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}

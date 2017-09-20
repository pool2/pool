import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiAlertService } from 'ng-jhipster';

import { FilterType } from './filter-type.model';
import { FilterTypeService } from './filter-type.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-filter-type',
    templateUrl: './filter-type.component.html'
})
export class FilterTypeComponent implements OnInit, OnDestroy {
filterTypes: FilterType[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private filterTypeService: FilterTypeService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.filterTypeService.query().subscribe(
            (res: ResponseWrapper) => {
                this.filterTypes = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInFilterTypes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: FilterType) {
        return item.id;
    }
    registerChangeInFilterTypes() {
        this.eventSubscriber = this.eventManager.subscribe('filterTypeListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}

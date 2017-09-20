import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { FilterType } from './filter-type.model';
import { FilterTypePopupService } from './filter-type-popup.service';
import { FilterTypeService } from './filter-type.service';

@Component({
    selector: 'jhi-filter-type-delete-dialog',
    templateUrl: './filter-type-delete-dialog.component.html'
})
export class FilterTypeDeleteDialogComponent {

    filterType: FilterType;

    constructor(
        private filterTypeService: FilterTypeService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.filterTypeService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'filterTypeListModification',
                content: 'Deleted an filterType'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-filter-type-delete-popup',
    template: ''
})
export class FilterTypeDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private filterTypePopupService: FilterTypePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.filterTypePopupService
                .open(FilterTypeDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Pool } from './pool.model';
import { PoolPopupService } from './pool-popup.service';
import { PoolService } from './pool.service';

@Component({
    selector: 'jhi-pool-delete-dialog',
    templateUrl: './pool-delete-dialog.component.html'
})
export class PoolDeleteDialogComponent {

    pool: Pool;

    constructor(
        private poolService: PoolService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.poolService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'poolListModification',
                content: 'Deleted an pool'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-pool-delete-popup',
    template: ''
})
export class PoolDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private poolPopupService: PoolPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.poolPopupService
                .open(PoolDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

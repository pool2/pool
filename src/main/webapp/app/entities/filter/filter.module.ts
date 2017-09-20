import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PoolSharedModule } from '../../shared';
import {
    FilterService,
    FilterPopupService,
    FilterComponent,
    FilterDetailComponent,
    FilterDialogComponent,
    FilterPopupComponent,
    FilterDeletePopupComponent,
    FilterDeleteDialogComponent,
    filterRoute,
    filterPopupRoute,
} from './';

const ENTITY_STATES = [
    ...filterRoute,
    ...filterPopupRoute,
];

@NgModule({
    imports: [
        PoolSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        FilterComponent,
        FilterDetailComponent,
        FilterDialogComponent,
        FilterDeleteDialogComponent,
        FilterPopupComponent,
        FilterDeletePopupComponent,
    ],
    entryComponents: [
        FilterComponent,
        FilterDialogComponent,
        FilterPopupComponent,
        FilterDeleteDialogComponent,
        FilterDeletePopupComponent,
    ],
    providers: [
        FilterService,
        FilterPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PoolFilterModule {}

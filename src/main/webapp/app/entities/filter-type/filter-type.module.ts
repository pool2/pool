import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PoolSharedModule } from '../../shared';
import {
    FilterTypeService,
    FilterTypePopupService,
    FilterTypeComponent,
    FilterTypeDetailComponent,
    FilterTypeDialogComponent,
    FilterTypePopupComponent,
    FilterTypeDeletePopupComponent,
    FilterTypeDeleteDialogComponent,
    filterTypeRoute,
    filterTypePopupRoute,
} from './';

const ENTITY_STATES = [
    ...filterTypeRoute,
    ...filterTypePopupRoute,
];

@NgModule({
    imports: [
        PoolSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        FilterTypeComponent,
        FilterTypeDetailComponent,
        FilterTypeDialogComponent,
        FilterTypeDeleteDialogComponent,
        FilterTypePopupComponent,
        FilterTypeDeletePopupComponent,
    ],
    entryComponents: [
        FilterTypeComponent,
        FilterTypeDialogComponent,
        FilterTypePopupComponent,
        FilterTypeDeleteDialogComponent,
        FilterTypeDeletePopupComponent,
    ],
    providers: [
        FilterTypeService,
        FilterTypePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PoolFilterTypeModule {}

import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PoolSharedModule } from '../../shared';
import {
    FilterBrandService,
    FilterBrandPopupService,
    FilterBrandComponent,
    FilterBrandDetailComponent,
    FilterBrandDialogComponent,
    FilterBrandPopupComponent,
    FilterBrandDeletePopupComponent,
    FilterBrandDeleteDialogComponent,
    filterBrandRoute,
    filterBrandPopupRoute,
} from './';

const ENTITY_STATES = [
    ...filterBrandRoute,
    ...filterBrandPopupRoute,
];

@NgModule({
    imports: [
        PoolSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        FilterBrandComponent,
        FilterBrandDetailComponent,
        FilterBrandDialogComponent,
        FilterBrandDeleteDialogComponent,
        FilterBrandPopupComponent,
        FilterBrandDeletePopupComponent,
    ],
    entryComponents: [
        FilterBrandComponent,
        FilterBrandDialogComponent,
        FilterBrandPopupComponent,
        FilterBrandDeleteDialogComponent,
        FilterBrandDeletePopupComponent,
    ],
    providers: [
        FilterBrandService,
        FilterBrandPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PoolFilterBrandModule {}

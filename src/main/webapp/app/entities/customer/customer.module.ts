import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { PoolSharedModule } from '../../shared';

import {
    CustomerService,
    CustomerPopupService,
    CustomerComponent,
    CustomerDetailComponent,
    CustomerDialogComponent,
    CustomerPopupComponent,
    CustomerDeletePopupComponent,
    CustomerDeleteDialogComponent,
    customerRoute,
    customerPopupRoute,
} from './';
import { PoolPoolModule } from '../pool/pool.module';
import { PoolComponent } from '../pool/pool.component';

const ENTITY_STATES = [
    ...customerRoute,
    ...customerPopupRoute,
];

@NgModule({
    imports: [
        PoolSharedModule,
        PoolPoolModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        CustomerComponent,
        CustomerDetailComponent,
        CustomerDialogComponent,
        CustomerDeleteDialogComponent,
        CustomerPopupComponent,
        CustomerDeletePopupComponent,
    ],
    entryComponents: [
        CustomerComponent,
        PoolComponent,
        CustomerDialogComponent,
        CustomerPopupComponent,
        CustomerDeleteDialogComponent,
        CustomerDeletePopupComponent,
    ],
    providers: [
        CustomerService,
        CustomerPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PoolCustomerModule {}

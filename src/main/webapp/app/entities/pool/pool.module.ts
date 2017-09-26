import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PoolSharedModule } from '../../shared';
import {
    PoolService,
    PoolPopupService,
    PoolComponent,
    PoolDetailComponent,
    PoolDialogComponent,
    PoolPopupComponent,
    PoolDeletePopupComponent,
    PoolDeleteDialogComponent,
    poolRoute,
    poolPopupRoute,
} from './';

const ENTITY_STATES = [
    ...poolRoute,
    ...poolPopupRoute,
];

@NgModule({
    imports: [
        PoolSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    exports: [
        PoolComponent
    ],
    declarations: [
        PoolComponent,
        PoolDetailComponent,
        PoolDialogComponent,
        PoolDeleteDialogComponent,
        PoolPopupComponent,
        PoolDeletePopupComponent,
    ],
    entryComponents: [
        PoolComponent,
        PoolDialogComponent,
        PoolPopupComponent,
        PoolDeleteDialogComponent,
        PoolDeletePopupComponent,
    ],
    providers: [
        PoolService,
        PoolPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PoolPoolModule {}

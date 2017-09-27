import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PoolSharedModule } from '../../shared';
import {
    AppointmentService,
    AppointmentPopupService,
    AppointmentComponent,
    AppointmentDetailComponent,
    AppointmentDialogComponent,
    AppointmentPopupComponent,
    AppointmentDeletePopupComponent,
    AppointmentDeleteDialogComponent,
    appointmentRoute,
    appointmentPopupRoute,
} from './';
import { AppointmentActiveComponent } from './appointment-active.component';

const ENTITY_STATES = [
    ...appointmentRoute,
    ...appointmentPopupRoute,
];

@NgModule({
    imports: [
        PoolSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    exports: [
        AppointmentActiveComponent
    ] ,
    declarations: [
        AppointmentComponent,
        AppointmentActiveComponent,
        AppointmentDetailComponent,
        AppointmentDialogComponent,
        AppointmentDeleteDialogComponent,
        AppointmentPopupComponent,
        AppointmentDeletePopupComponent,
    ],
    entryComponents: [
        AppointmentComponent,
        AppointmentActiveComponent,
        AppointmentDialogComponent,
        AppointmentPopupComponent,
        AppointmentDeleteDialogComponent,
        AppointmentDeletePopupComponent,
    ],
    providers: [
        AppointmentService,
        AppointmentPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PoolAppointmentModule {}

import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PoolSharedModule } from '../shared';

import { HOME_ROUTE, HomeComponent } from './';
import { PoolAppointmentModule } from '../entities/appointment/appointment.module';
import { AppointmentActiveComponent } from '../entities/appointment/appointment-active.component';

@NgModule({
    imports: [
        PoolSharedModule,
        PoolAppointmentModule,
        RouterModule.forRoot([ HOME_ROUTE ], { useHash: true })
    ],
    declarations: [
        HomeComponent,
    ],
    entryComponents: [
       AppointmentActiveComponent
    ],
    providers: [
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PoolHomeModule {}

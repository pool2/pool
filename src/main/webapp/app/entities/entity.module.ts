import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { PoolCompanyModule } from './company/company.module';
import { PoolCustomerModule } from './customer/customer.module';
import { PoolPoolModule } from './pool/pool.module';
import { PoolAppointmentModule } from './appointment/appointment.module';
import { PoolImageModule } from './image/image.module';
import { PoolInventoryItemModule } from './inventory-item/inventory-item.module';
import { PoolInventoryUsedModule } from './inventory-used/inventory-used.module';
import { PoolEmployeeModule } from './employee/employee.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        PoolCompanyModule,
        PoolCustomerModule,
        PoolPoolModule,
        PoolAppointmentModule,
        PoolImageModule,
        PoolInventoryItemModule,
        PoolInventoryUsedModule,
        PoolEmployeeModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PoolEntityModule {}

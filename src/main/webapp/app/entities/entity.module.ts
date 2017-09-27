import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { PoolCompanyModule } from './company/company.module';
import { PoolCustomerModule } from './customer/customer.module';
import { PoolPoolModule } from './pool/pool.module';
import { PoolAppointmentModule } from './appointment/appointment.module';
import { PoolImageModule } from './image/image.module';
import { PoolInventoryItemModule } from './inventory-item/inventory-item.module';
import { PoolInventoryUsedModule } from './inventory-used/inventory-used.module';
import { PoolEmployeeModule } from './employee/employee.module';
import { PoolTaskModule } from './task/task.module';
import { PoolFilterModule } from './filter/filter.module';
import { PoolFilterBrandModule } from './filter-brand/filter-brand.module';
import { PoolFilterTypeModule } from './filter-type/filter-type.module';
import { PoolMaterialModule } from './material/material.module';
import { PoolNoteModule } from './note/note.module';
import { PoolWaterTestModule } from './water-test/water-test.module';
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
        PoolTaskModule,
        PoolFilterModule,
        PoolFilterBrandModule,
        PoolFilterTypeModule,
        PoolMaterialModule,
        PoolNoteModule,
        PoolWaterTestModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    exports: [
        PoolPoolModule,
        PoolAppointmentModule
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PoolEntityModule {}

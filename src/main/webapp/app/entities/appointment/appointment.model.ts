import { BaseEntity } from './../../shared';

export class Appointment implements BaseEntity {
    constructor(
        public id?: number,
        public startTime?: any,
        public endTime?: any,
        public notes?: any,
        public image?: BaseEntity,
        public employee?: BaseEntity,
        public pool?: BaseEntity,
        public inventoryUseds?: BaseEntity[],
    ) {
    }
}

import { BaseEntity } from './../../shared';

export class Appointment implements BaseEntity {
    constructor(
        public id?: number,
        public startTime?: any,
        public endTime?: any,
        public waterTest?: BaseEntity,
        public waterTests?: BaseEntity[],
        public images?: BaseEntity[],
        public employee?: BaseEntity,
        public pool?: BaseEntity,
        public note?: BaseEntity,
        public inventoryUseds?: BaseEntity[],
        public tasks?: BaseEntity[],
    ) {
    }
}

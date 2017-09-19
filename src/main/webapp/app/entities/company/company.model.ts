import { BaseEntity } from './../../shared';

export class Company implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public phoneNumber?: string,
        public employees?: BaseEntity[],
        public customers?: BaseEntity[],
        public inventoryItems?: BaseEntity[],
    ) {
    }
}

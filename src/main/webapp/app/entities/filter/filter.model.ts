import { BaseEntity } from './../../shared';

export class Filter implements BaseEntity {
    constructor(
        public id?: number,
        public type?: string,
        public size?: number,
        public modelNumber?: string,
        public replacedDate?: any,
        public filterTypes?: BaseEntity[],
        public filterBrands?: BaseEntity[],
        public note?: BaseEntity,
    ) {
    }
}

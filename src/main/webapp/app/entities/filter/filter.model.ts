import { BaseEntity } from './../../shared';

export class Filter implements BaseEntity {
    constructor(
        public id?: number,
        public type?: string,
        public size?: number,
        public modelNumber?: string,
        public brand?: string,
        public replacedDate?: any,
        public note?: BaseEntity,
        public filterBrand?: BaseEntity,
        public filterType?: BaseEntity,
    ) {
    }
}

import { BaseEntity } from './../../shared';

export class FilterType implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
    ) {
    }
}

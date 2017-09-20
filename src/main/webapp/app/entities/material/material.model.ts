import { BaseEntity } from './../../shared';

export class Material implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
    ) {
    }
}

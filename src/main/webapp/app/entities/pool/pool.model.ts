import { BaseEntity } from './../../shared';

export class Pool implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public size?: number,
        public note?: string,
        public customer?: BaseEntity,
        public filter?: BaseEntity,
        public material?: BaseEntity,
    ) {
    }
}

import { BaseEntity } from './../../shared';

export class Pool implements BaseEntity {
    constructor(
        public id?: number,
        public size?: number,
        public customer?: BaseEntity,
        public note?: BaseEntity,
    ) {
    }
}

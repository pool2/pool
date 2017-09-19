import { BaseEntity } from './../../shared';

export class InventoryItem implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public quantity?: number,
    ) {
    }
}

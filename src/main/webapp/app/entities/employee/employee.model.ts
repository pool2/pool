import { BaseEntity, User } from './../../shared';

export class Employee implements BaseEntity {
    constructor(
        public id?: number,
        public phoneNumber?: string,
        public company?: BaseEntity,
        public user?: User,
    ) {
    }
}

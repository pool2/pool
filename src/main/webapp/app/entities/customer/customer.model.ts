import { BaseEntity } from './../../shared';

export class Customer implements BaseEntity {
    constructor(
        public id?: number,
        public firstName?: string,
        public lastName?: string,
        public phoneNumber?: string,
        public altPhoneNumber?: string,
        public email?: string,
        public address1?: string,
        public address2?: string,
        public address3?: string,
        public city?: string,
        public state?: string,
        public zip?: string,
        public company?: BaseEntity,
        public pools?: BaseEntity[],
    ) {
    }
}

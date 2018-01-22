import { BaseEntity } from './../../shared';

export class InquiryMySuffix implements BaseEntity {
    constructor(
        public id?: number,
        public createdDate?: any,
        public email?: string,
        public firstName?: string,
        public lastName?: string,
        public phoneNumber?: string,
        public country?: string,
        public region?: string,
        public provice?: string,
        public city?: string,
        public postalCode?: string,
    ) {
    }
}

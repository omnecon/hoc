import { BaseEntity } from './../../shared';

export const enum ProPackage {
    'FREE',
    'PRO_STANDARD',
    'PRO_ADVANCED',
    'VIP'
}

export const enum Gender {
    'F',
    'M'
}

export class ProfileMySuffix implements BaseEntity {
    constructor(
        public id?: number,
        public createdDate?: any,
        public lastUpdatedDate?: any,
        public published?: boolean,
        public url?: string,
        public branch?: string,
        public title?: string,
        public email?: string,
        public firstName?: string,
        public lastName?: string,
        public phoneNumber?: string,
        public score?: number,
        public proPackage?: ProPackage,
        public lng?: number,
        public lat?: number,
        public country?: string,
        public city?: string,
        public state?: string,
        public street?: string,
        public postCode?: string,
        public gender?: Gender,
        public agbCheck?: boolean,
        public status?: string,
        public locType?: string,
        public locGeo?: string,
        public locCapacity?: string,
        public spAvailableRegion?: string,
        public featureStr?: string,
        public imgUrl?: string,
        public imgTitle?: string,
        public imgAlt?: string,
    ) {
        this.published = false;
        this.agbCheck = false;
    }
}

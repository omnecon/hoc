import { BaseEntity } from './../../shared';

export const enum GalleryType {
    'PORTFOLIO',
    'LOCATION',
    'COMMUNITY'
}

export class GalleryMySuffix implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public type?: GalleryType,
    ) {
    }
}

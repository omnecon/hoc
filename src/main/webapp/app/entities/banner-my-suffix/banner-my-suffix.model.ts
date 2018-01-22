import { BaseEntity } from './../../shared';

export class BannerMySuffix implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
    ) {
    }
}

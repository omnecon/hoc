import { BaseEntity } from './../../shared';

export class FeatureMySuffix implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public profiles?: BaseEntity[],
    ) {
    }
}

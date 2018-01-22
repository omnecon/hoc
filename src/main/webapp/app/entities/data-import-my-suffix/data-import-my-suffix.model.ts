import { BaseEntity } from './../../shared';

export class DataImportMySuffix implements BaseEntity {
    constructor(
        public id?: number,
        public type?: string,
    ) {
    }
}

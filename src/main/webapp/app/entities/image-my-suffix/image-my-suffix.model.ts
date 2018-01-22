import { BaseEntity } from './../../shared';

export class ImageMySuffix implements BaseEntity {
    constructor(
        public id?: number,
        public title?: string,
        public alt?: string,
        public caption?: string,
        public imgOriginal?: string,
        public imgLarge?: string,
        public imgThumbnail?: string,
    ) {
    }
}

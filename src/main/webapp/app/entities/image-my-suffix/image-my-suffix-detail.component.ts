import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { ImageMySuffix } from './image-my-suffix.model';
import { ImageMySuffixService } from './image-my-suffix.service';

@Component({
    selector: 'jhi-image-my-suffix-detail',
    templateUrl: './image-my-suffix-detail.component.html'
})
export class ImageMySuffixDetailComponent implements OnInit, OnDestroy {

    image: ImageMySuffix;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private imageService: ImageMySuffixService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInImages();
    }

    load(id) {
        this.imageService.find(id).subscribe((image) => {
            this.image = image;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInImages() {
        this.eventSubscriber = this.eventManager.subscribe(
            'imageListModification',
            (response) => this.load(this.image.id)
        );
    }
}

import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { FeatureMySuffix } from './feature-my-suffix.model';
import { FeatureMySuffixPopupService } from './feature-my-suffix-popup.service';
import { FeatureMySuffixService } from './feature-my-suffix.service';
import { ProfileMySuffix, ProfileMySuffixService } from '../profile-my-suffix';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-feature-my-suffix-dialog',
    templateUrl: './feature-my-suffix-dialog.component.html'
})
export class FeatureMySuffixDialogComponent implements OnInit {

    feature: FeatureMySuffix;
    isSaving: boolean;

    profiles: ProfileMySuffix[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private featureService: FeatureMySuffixService,
        private profileService: ProfileMySuffixService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.profileService.query()
            .subscribe((res: ResponseWrapper) => { this.profiles = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.feature.id !== undefined) {
            this.subscribeToSaveResponse(
                this.featureService.update(this.feature));
        } else {
            this.subscribeToSaveResponse(
                this.featureService.create(this.feature));
        }
    }

    private subscribeToSaveResponse(result: Observable<FeatureMySuffix>) {
        result.subscribe((res: FeatureMySuffix) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: FeatureMySuffix) {
        this.eventManager.broadcast({ name: 'featureListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackProfileById(index: number, item: ProfileMySuffix) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}

@Component({
    selector: 'jhi-feature-my-suffix-popup',
    template: ''
})
export class FeatureMySuffixPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private featurePopupService: FeatureMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.featurePopupService
                    .open(FeatureMySuffixDialogComponent as Component, params['id']);
            } else {
                this.featurePopupService
                    .open(FeatureMySuffixDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

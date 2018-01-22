import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ProfileMySuffix } from './profile-my-suffix.model';
import { ProfileMySuffixPopupService } from './profile-my-suffix-popup.service';
import { ProfileMySuffixService } from './profile-my-suffix.service';
import { FeatureMySuffix, FeatureMySuffixService } from '../feature-my-suffix';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-profile-my-suffix-dialog',
    templateUrl: './profile-my-suffix-dialog.component.html'
})
export class ProfileMySuffixDialogComponent implements OnInit {

    profile: ProfileMySuffix;
    isSaving: boolean;

    features: FeatureMySuffix[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private profileService: ProfileMySuffixService,
        private featureService: FeatureMySuffixService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.featureService.query()
            .subscribe((res: ResponseWrapper) => { this.features = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.profile.id !== undefined) {
            this.subscribeToSaveResponse(
                this.profileService.update(this.profile));
        } else {
            this.subscribeToSaveResponse(
                this.profileService.create(this.profile));
        }
    }

    private subscribeToSaveResponse(result: Observable<ProfileMySuffix>) {
        result.subscribe((res: ProfileMySuffix) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: ProfileMySuffix) {
        this.eventManager.broadcast({ name: 'profileListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackFeatureById(index: number, item: FeatureMySuffix) {
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
    selector: 'jhi-profile-my-suffix-popup',
    template: ''
})
export class ProfileMySuffixPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private profilePopupService: ProfileMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.profilePopupService
                    .open(ProfileMySuffixDialogComponent as Component, params['id']);
            } else {
                this.profilePopupService
                    .open(ProfileMySuffixDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { StatisticMySuffix } from './statistic-my-suffix.model';
import { StatisticMySuffixPopupService } from './statistic-my-suffix-popup.service';
import { StatisticMySuffixService } from './statistic-my-suffix.service';
import { ProfileMySuffix, ProfileMySuffixService } from '../profile-my-suffix';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-statistic-my-suffix-dialog',
    templateUrl: './statistic-my-suffix-dialog.component.html'
})
export class StatisticMySuffixDialogComponent implements OnInit {

    statistic: StatisticMySuffix;
    isSaving: boolean;

    profiles: ProfileMySuffix[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private statisticService: StatisticMySuffixService,
        private profileService: ProfileMySuffixService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.profileService
            .query({filter: 'statistic-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.statistic.profile || !this.statistic.profile.id) {
                    this.profiles = res.json;
                } else {
                    this.profileService
                        .find(this.statistic.profile.id)
                        .subscribe((subRes: ProfileMySuffix) => {
                            this.profiles = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.statistic.id !== undefined) {
            this.subscribeToSaveResponse(
                this.statisticService.update(this.statistic));
        } else {
            this.subscribeToSaveResponse(
                this.statisticService.create(this.statistic));
        }
    }

    private subscribeToSaveResponse(result: Observable<StatisticMySuffix>) {
        result.subscribe((res: StatisticMySuffix) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: StatisticMySuffix) {
        this.eventManager.broadcast({ name: 'statisticListModification', content: 'OK'});
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
}

@Component({
    selector: 'jhi-statistic-my-suffix-popup',
    template: ''
})
export class StatisticMySuffixPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private statisticPopupService: StatisticMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.statisticPopupService
                    .open(StatisticMySuffixDialogComponent as Component, params['id']);
            } else {
                this.statisticPopupService
                    .open(StatisticMySuffixDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

/* global _, angular, UHGroupingsApp */

(() => {
    /**
     * This controller contains functions specific to the announcements seen in the home page.
     * @param $scope - binding between controller and HTML page
     * @param $controller - service for instantiating controllers
     * @param groupingsService - service for creating requests to the groupings API
     */
    function AnnouncementsJsController($scope, $controller, groupingsService) {
        angular.extend(this, $controller("GeneralJsController", {$scope}));
        /**
         * Load the valid outage messages into outageMessage,
         * otherwise display an API error modal.
         */
        $scope.init = () => {
            $scope.initDone = false;
            groupingsService.getAnnouncements((res) => {
                $scope.activeAnnouncements = $scope.handleActiveAnnouncements(res.announcements);
                setTimeout(() => {
                    $scope.initDone = true;
                }, 1000);

                    //$scope.activeAnnouncements = ['hello','goodbye'];
                    //$scope.activeAnnouncementsLength = $scope.activeAnnouncements.length;
                }, () => {
                    $scope.displayApiErrorModal();
                }
            );
        };

        $scope.handleActiveAnnouncements = (announcements) =>
            announcements
                .filter((announcement) => announcement.state === "Active")
                .map((announcement) => announcement.message);

    }

    UHGroupingsApp.controller("AnnouncementsJsController", AnnouncementsJsController);
})();